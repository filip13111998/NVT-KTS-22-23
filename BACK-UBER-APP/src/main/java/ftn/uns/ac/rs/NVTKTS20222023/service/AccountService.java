package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.*;
import ftn.uns.ac.rs.NVTKTS20222023.exception.TwoPasswordsNotSameException;
import ftn.uns.ac.rs.NVTKTS20222023.mail.EmailService;
import ftn.uns.ac.rs.NVTKTS20222023.model.*;
import ftn.uns.ac.rs.NVTKTS20222023.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private DriverRepository dr;

    @Autowired
    private AdminRepository ar;

    @Autowired
    private VehicleRepository vr;

    @Autowired
    private LocationRepository lr;

    @Autowired
    private ModelMapper mm;

    @Autowired
    private PasswordEncoder ps;

    @Autowired
    private EmailService es;

    public Boolean registerCitizen(RegisterCitizenDTO rcdto) {
        System.out.println("Register Citizen");
        Citizen citizen = mm.map(rcdto , Citizen.class);

        if(!rcdto.getPassword().equals(rcdto.getConfirm())){
            throw new TwoPasswordsNotSameException("TWO PASSWORDS NOT SAME");
        }

        citizen.setPassword(ps.encode(citizen.getPassword()));

        cr.save(citizen);
        System.out.println("Saved Citizen");
        String usrn = citizen.getUsername().split("\\@")[0] + "_" + citizen.getUsername().split("\\@")[1].split("\\.")[0];
        //"<a href=\"localhost:8080/verify/" + usrn + "\">Click here to verify your account</a>"
        es.sendEmail(
                "eugene4@ethereal.email",
                "VERIFY ACCOUNT " + citizen.getUsername() ,
                "<a href=\"http://localhost:8080/auth/verify/" + usrn + "\">Click here to verify your account</a>"

        );

        return true;
    }

    public Boolean registerDriver(RegisterDriverDTO rddto) {

        Driver driver = mm.map(rddto , Driver.class);

        if(!rddto.getPassword().equals(rddto.getConfirm())){
            throw new TwoPasswordsNotSameException("TWO PASSWORDS NOT SAME");
        }
//        System.out.println(rddto.getVehicle().getName());
//        System.out.println(driver.getVehicle().getName());
        driver.setPassword(ps.encode(driver.getPassword()));
        driver.setVehicle(new Vehicle());
        driver.getVehicle().setName(rddto.getName());
        driver.getVehicle().setType(rddto.getType());
        driver.getVehicle().setPetFriendly(rddto.isPetFriendly());
        driver.getVehicle().setBabyFriendly(rddto.isBabyFriendly());

        driver.getVehicle().setBusy(false);

        //SET DEFAULT LATITUDE AND LONGITUDE FOR VEHICLE: 45.252107 - 19.836915
        Location l = Location.builder().Latitude(45.252107).longitude(19.836915).build();

        driver.getVehicle().setLocation(l);

        lr.save(l);

        vr.save(driver.getVehicle());

        dr.save(driver);

        return true;
    }

    public Boolean verify(String username) {

        String usnm = username.split("\\_")[0]+ "@" +  username.split("\\_")[1] + ".com";

        Citizen citizen = cr.findByUsername(usnm);

        citizen.setVerify(true);

        return true;

    }

    public Boolean passwordResetCitizen(ResetPasswordDTO rpdto) {
        Citizen c = cr.findByUsername(rpdto.getUsername());

        if(!rpdto.getPassword().equals(rpdto.getConfirm())){
            throw new TwoPasswordsNotSameException("TWO PASSWORDS NOT SAME");
        }
        c.setPassword(ps.encode(rpdto.getPassword()));

        return true;
    }

    public Boolean passwordResetDriver(ResetPasswordDTO rpdto) {
        Driver d = dr.findByUsername(rpdto.getUsername());

        if(!rpdto.getPassword().equals(rpdto.getConfirm())){
            throw new TwoPasswordsNotSameException("TWO PASSWORDS NOT SAME");
        }
        d.setPassword(ps.encode(rpdto.getPassword()));

        return true;
    }

    public Boolean passwordResetAdmin(ResetPasswordDTO rpdto) {
        Admin a = ar.findByUsername(rpdto.getUsername());

        if(!rpdto.getPassword().equals(rpdto.getConfirm())){
            throw new TwoPasswordsNotSameException("TWO PASSWORDS NOT SAME");
        }
        a.setPassword(ps.encode(rpdto.getPassword()));

        return true;
    }


    public Boolean changeStateDriver(ChangeDriverStateDTO cdsdto) {

        Driver driver = dr.findByUsername(cdsdto.getUsername());

        driver.setActive(cdsdto.isFlag());

        return true;

    }

    public Boolean logout(String username) {

        ChangeDriverStateDTO cdsdto = ChangeDriverStateDTO.builder().username(username).flag(false).build();

        Boolean answer = this.changeStateDriver(cdsdto);

        return answer;

    }

    public Boolean updateAdminProfile(AdminUpdateDTO audto, MultipartFile file){

        // Convert AdminDTO to Citizen object
        Admin admin = ar.findByUsername(audto.getUsername());
        admin.setPassword(ps.encode(audto.getPassword()));
        admin.setFirstName(audto.getFirstName());
        admin.setLastName(audto.getLastName());
        admin.setPhone(audto.getPhone());
        admin.setCity(audto.getCity());
//        citizen.setImage(uudto.getImage());

        // Save image file to resource folder
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            String uploadPath = "src/main/resources/admins/";
            Path path = Paths.get(uploadPath + "/" + file.getOriginalFilename());
            try {
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

//            String folderPath = "C:\\Users\\vaske\\Desktop\\NVT-KTS\\NVT-KTS-22-23\\BACK-UBER-APP\\src\\main\\resources\\admins\\";
//            File imageFile = new File(folderPath + fileName);
//            try {
//                file.transferTo(imageFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            admin.setImage(fileName);
        }

        return true;
    }

    public Boolean updateDriverProfile(DriverUpdateDTO dudto, MultipartFile file) {

        // Convert DriverDTO to Citizen object
        Driver driver = dr.findByUsername(dudto.getUsername());

        if(dudto.getCity() != null || dudto.getPassword() != null || dudto.getPhone() != null || dudto.getLastName() != null
                || dudto.getFirstName() != null || file!= null){
            driver.setEdit(true);
        }

        driver.setRepassword(ps.encode(dudto.getPassword()));
        driver.setRefirstName(dudto.getFirstName());
        driver.setRelastName(dudto.getLastName());
        driver.setRephone(dudto.getPhone());
        driver.setRecity(dudto.getCity());

        // Save image file to resource folder
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            String uploadPath = "src/main/resources/drivers/";
            Path path = Paths.get(uploadPath + "/" + file.getOriginalFilename());
            try {
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            driver.setReimage(fileName);
        }

        return true;

    }

    public Boolean updateCitizenProfile(CitizenUpdateDTO cudto, MultipartFile file) {

        // Convert CitizenDTO to Citizen object
        Citizen citizen = cr.findByUsername(cudto.getUsername());
        citizen.setPassword(ps.encode(cudto.getPassword()));
        citizen.setFirstName(cudto.getFirstName());
        citizen.setLastName(cudto.getLastName());
        citizen.setPhone(cudto.getPhone());
        citizen.setCity(cudto.getCity());
        citizen.setTokens(cudto.getTokens());

        // Save image file to resource folder
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            String uploadPath = "src/main/resources/drivers/";
            Path path = Paths.get(uploadPath + "/" + file.getOriginalFilename());
            try {
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            citizen.setImage(fileName);
        }

        return true;
    }

    public List<String> getAllUpdatedDrivers() {

        List<String> drivers = dr.findAll().stream().filter(d-> d.isEdit()).map(d->d.getUsername()).collect(Collectors.toList());

        return drivers;

    }

    public Boolean acceptDriverUpdate(String username) {

        Driver driver = dr.findByUsername(username);

        if(driver.isEdit()){
            driver.setPassword(driver.getRepassword());
            driver.setFirstName(driver.getRefirstName());
            driver.setLastName(driver.getRelastName());
            driver.setPhone(driver.getRephone());
            driver.setCity(driver.getRecity());

            return true;
        }

        return false;
    }


    public Boolean saveCitizenByUsername(String username) {
        Citizen citizen = new Citizen();
        citizen.setUsername(username);

        Citizen c = cr.findByUsername(username);

        if(c == null){
            System.out.println("KORISNIK SA USERNAME JE DODATA -> " + username);
            cr.save(citizen);
            return true;
        }
        System.out.println("KORISNIK SA USERNAME POSTOJI -> " + username);
        return false;
    }
}
