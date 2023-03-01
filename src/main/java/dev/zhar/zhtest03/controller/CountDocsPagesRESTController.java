package dev.zhar.zhtest03.controller;

import dev.zhar.zhtest03.service.CountDocsPages;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("")
public class CountDocsPagesRESTController {

    private final CountDocsPages countDocsPages;

    public CountDocsPagesRESTController(CountDocsPages countDocsPages) {
        this.countDocsPages = countDocsPages;
    }

    @GetMapping("/address") // Адрес принимается в виде "address?addr=F:/Drivers"
    public String getHouseIdByAddress1(@RequestParam String addr) {
        File dir = new File(addr);
        Integer[] result = countDocsPages.FileList(dir);

        return "Documents: " + result[0].toString() + "<br> Pages: " +  result[1].toString();
    }
}
