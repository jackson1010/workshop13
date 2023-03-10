package TFIP.workshop14.redo.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import TFIP.workshop14.redo.Model.ContactModel;

@Component
public class ContactServices {
    @Value("${dataDir}")
    private String dataDir;

    public void saveContact(ContactModel contact, Model model) {
        String fileName = contact.getId();
        PrintWriter pw = null;

        try {
            FileWriter fw = new FileWriter(dataDir + "/" + fileName);
            pw = new PrintWriter(fw);
            pw.println(contact.getName());
            pw.println(contact.getEmail());
            pw.println(contact.getPhoneNumber());
            pw.println(contact.getDateOfBirth());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute(contact);
    }

    public boolean getContactByid(Model model, String id) {
        ContactModel contact = new ContactModel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean idFound = false;
        try {
            // creates a file object
            // the toPath() convert it to a path object
            // is requried as reallAllLines() need path object
            Path filePath = new File(dataDir + "/" + id).toPath();

            Charset cset = Charset.forName("UTF-8");
            List<String> fileValues = Files.readAllLines(filePath, cset);

            if (!fileValues.isEmpty()) {
                idFound = true;
            }

            contact.setId(id);
            contact.setName(fileValues.get(0));
            contact.setEmail(fileValues.get(1));
            contact.setPhoneNumber(fileValues.get(2));
            LocalDate dob = LocalDate.parse(fileValues.get(3), formatter);
            contact.setDateOfBirth(dob);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("contactModel", contact);

        return idFound;
    }

    public void getAllContacts(Model model) {
        Set<String> dataFiles = listFiles(dataDir);
        model.addAttribute("contactList", dataFiles);
    }

    private Set<String> listFiles(String dataDir) {
        // return Stream.of(new File(dataDir).listFiles()).filter(file->!file.isDirectory()).map(File::getName).collect(Collectors.toSet());
        
        // Create a File object for the specified directory
        File directory = new File(dataDir);

        // Get an array of File objects representing the files in the directory
        File[] files = directory.listFiles();

        // Filter out any files that are directories
        Stream<File> nonDirectories = Stream.of(files).filter(file -> !file.isDirectory());

        // Map each remaining File object to its name as a String
        Stream<String> fileNames = nonDirectories.map(file -> file.getName());

        // Collect the resulting Strings into a Set
        Set<String> fileNamesSet = fileNames.collect(Collectors.toSet());

        // Return the Set of file names
        return fileNamesSet;

    }

}
