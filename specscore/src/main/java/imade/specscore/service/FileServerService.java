package imade.specscore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServerService {
    private final Path rootLocation = Paths.get("upload-dir");

    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            file.transferTo(destinationFile);
            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    public ResponseEntity<ResourceRegion> streamingVedio(HttpHeaders httpHeaders, String pathStr) {
        try{
            Path path = Paths.get(pathStr);
            Resource resource = new FileSystemResource(path);
            long chunkSize = 1024*1024;
            long contentLength = resource.contentLength();
            ResourceRegion resourceRegion;
            try{
                HttpRange httpRange;
                if(httpHeaders.getRange().stream().findFirst().isPresent()){
                    httpRange = httpHeaders.getRange().stream().findFirst().get();
                    long start = httpRange.getRangeStart(contentLength);
                    long end = httpRange.getRangeEnd(contentLength);
                    long rangeLength = Long.min(chunkSize, end-start+1);

                    resourceRegion = new ResourceRegion(resource, start, rangeLength);
                }
                else{
                    resourceRegion = new ResourceRegion(resource, 0, Long.min(chunkSize, resource.contentLength()));
                }
            }
            catch(Exception e){
                long rangeLength = Long.min(chunkSize, resource.contentLength());
                resourceRegion = new ResourceRegion(resource, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 10분
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resourceRegion);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
