package com.Focus.Reddit.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.Focus.Reddit.dto.CountryResponse;
import com.Focus.Reddit.dto.CustomerDto;
import com.Focus.Reddit.dto.CustomerResponse;
import com.Focus.Reddit.model.ImageModel;
import com.Focus.Reddit.repository.ImageRepository;
import com.Focus.Reddit.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletContext;

import static org.springframework.http.ResponseEntity.status;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/customers/")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    @Autowired
    ServletContext context;
    @Autowired
    ImageRepository imageRepository;
    private final CustomerService customerService;


    /* Image*/

//    @PostMapping(value="/upload" ,
//, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value="/upload")
    public ResponseEntity<Void> uplaodImage(
            @RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        customerService.saveImage(img);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    //    @GetMapping(path = {"/get/{imageName}"})
    @RequestMapping(value = "/get/{imageName}", method = RequestMethod.GET ,produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<ImageModel> getImage(@PathVariable("imageName") String imageName) throws IOException {
        System.out.println("imageName >>>" + imageName+".png");
        ImageModel retrievedImage = customerService.getImage(imageName+".png");


        return status(HttpStatus.OK).body( new ImageModel(retrievedImage.getName(), retrievedImage.getType(),
                decompressBytes(retrievedImage.getPicByte())));

    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


    /*
     * End 0f Image PART
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * */
    @PostMapping("/add-country")
    public ResponseEntity<Void> createCountry(@RequestBody CountryResponse saveCountry) {
        customerService.saveCountry(saveCountry.getCountryName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PostMapping("/images")
//    public ResponseEntity<ResponseBody> createArticle(@RequestParam("file") MultipartFile file,
//                                                      @RequestParam("article") String article) throws JsonParseException, JsonMappingException, Exception {
//        System.out.println("Ok .............");
////        Article arti = new ObjectMapper().readValue(article, Article.class);
//        boolean isExit = new File(context.getRealPath("/Images/")).exists();
//        if (!isExit) {
//            new File(context.getRealPath("/Images/")).mkdir();
//            System.out.println("mk dir.............");
//        }
//        String filename = file.getOriginalFilename();
//        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
//        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
//        try {
//            System.out.println("Image");
//            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        arti.setFileName(newFileName);
//        Article art = repository.save(arti);
//        if (art != null) {
//            return new ResponseEntity<Response>(new Response(""), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<Response>(new Response("Article not saved"), HttpStatus.BAD_REQUEST);
//        }
//    }

    /*
     *
     *
     *
     *
     * GET IMAGES
     *
     *
     *
     *
     * */
//    @GetMapping(path = "/Imgarticles/{id}")
//    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {
//        Article Article = repository.findById(id).get();
//        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + Article.getFileName()));
//    }

    /*
     *
     *
     *
     * Add Customer
     *
     *
     *
     * */
    @PostMapping
    public ResponseEntity<Void> createCustomers(@RequestBody CustomerDto commentDto
            /*, @RequestParam("file") MultipartFile file*/) {

//        boolean isExit = new File(context.getRealPath("/Images/")).exists();
//        if (!isExit) {
//            new File(context.getRealPath("/Images/")).mkdir();
//            System.out.println("mk dir.............");
//        }
//        String filename = file.getOriginalFilename();
//        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
//        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
//
//        if (!file.isEmpty()) {
//            try {
//
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(serverFile);
////                                new File(resources.getProperty("upload-dir")+
////                                        "/"+file.getOriginalFilename())));
//                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
//                ImageIO.write(bufferedImage, "image/jpeg", stream);
//                stream.write(bytes);
//                stream.close();
//
//            } catch (Exception e) {
//            }
//
//        }


        customerService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/{postId}")
//    public ResponseEntity<List<CustomerDto>> getAllCommentsForPosts(@PathVariable Long postId) {
//        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPosts(postId));
//    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<CustomerResponse>> getAllCommentsForUSer(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomersByUserName(userName));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCommentsForUSer() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }


}
