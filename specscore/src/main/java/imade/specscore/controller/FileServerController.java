package imade.specscore.controller;

import imade.specscore.service.FileServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileServerController {
    private final FileServerService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String filePath = service.saveFile(file);
        return ResponseEntity.ok(filePath);
    }

    @GetMapping("vedio/{info}")
    public ResponseEntity<ResourceRegion> streamingVedio(@RequestHeader HttpHeaders httpHeaders,
                                                         @PathVariable String info) {
        return service.streamingVedio(httpHeaders, info);
    }
}
