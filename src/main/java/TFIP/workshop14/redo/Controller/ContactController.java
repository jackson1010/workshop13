package TFIP.workshop14.redo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import TFIP.workshop14.redo.Component.ContactServices;
import TFIP.workshop14.redo.Model.ContactModel;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactServices cts;

    @GetMapping("/contactpage")
    public String showContactPage(Model model) {
        model.addAttribute("contactModel", new ContactModel());
        return "addressbook";
    }

    // need @ModelAttribute to link the model added in the GET method to the @valid
    // in the post method
    @PostMapping
    public String saveContact(@Valid @ModelAttribute("contactModel") ContactModel contactModel, BindingResult binding,
            Model model) {
        if (binding.hasErrors()) {
            return "addressbook";
        }
        cts.saveContact(contactModel, model);
        return "showContact";
    }

    @GetMapping("/{id}")
    public String getContact(Model model, @PathVariable String id) {
        boolean foundID= cts.getContactByid(model, id);
        System.out.println(foundID);
        if (foundID) {
            return "showContact";
        } else {
            return "error";
        }
    }

    @GetMapping("/list")
    public String getAllContacts(Model model) {
        cts.getAllContacts(model);
        return "allContacts";
    }

}
