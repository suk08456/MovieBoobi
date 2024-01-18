package com.korea.MOVIEBOOK.webtoon.webtoonList;


import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.webtoon.webtoonDayList.WebtoonDayList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
@Entity
public class Webtoon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private Integer fanCount;

    private Long webtoonId;

    private String title;

    private String author;

    private String imageUrl;

    private String searchKeyword;

//    @Column(name = "update_days")
//    private String updateDays;


    private String detailUrl;

    @OneToMany(mappedBy = "webtoonList")
    private List<WebtoonDayList> webtoonDayLists;

    @OneToMany(mappedBy = "webtoon")
    private List<Review> reviewList;
}


//    @RequestMapping("/regist/menu")
//    public String uploadMenu(MultipartHttpServletRequest mre, Model model, @RequestParam Long placeOwnerId, @RequestParam String name, @RequestParam String price) throws IOException {
//
//        MultipartFile mf = mre.getFile("file");
//        String uploadPath = "";
//        PlaceOwner owner = this.placeService.findById(placeOwnerId);
//
//        String path = "C:\\"+"place\\"+"menu\\";
//
//        File Folder = new File(path);
//        if (!Folder.exists()) {
//            Folder.mkdirs();
//        }
//
//        Path directoryPath = Paths.get(path);
//        Files.createDirectories(directoryPath);
//
//        String origional = mf.getOriginalFilename();
//
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String uniqueFileName = timestamp + "_" + origional;
//
//        uploadPath = path+uniqueFileName;
//
//        try{
//            mf.transferTo(new File(uploadPath)); // 파일 저장
//            this.placeMenuService.savefile(uniqueFileName,uploadPath,owner,name,price);
//        } catch (IllegalStateException | IOException e){
//            e.printStackTrace();;
//        }
//        model.addAttribute("uploadPath",uploadPath);
//        return "redirect:/place/map/regist/menu/detail/" + placeOwnerId;
//    }