package net.javaguides.springbootthymeleafcrudwebapp.controller;
import org.springframework.http.HttpHeaders;

import net.javaguides.springbootthymeleafcrudwebapp.model.Employee;
import net.javaguides.springbootthymeleafcrudwebapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class EmployeeController {
    //display list of Employees
   @Autowired
   private EmployeeService employeeService;
    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees",employeeService.getAllEmployees());
        return "index";
    }
    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployeeForm";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        // Save the employee using the EmployeeService
        employeeService.addEmployee(employee);
        // Redirect back to the home page or any other desired page
        return "redirect:/";
    }
    // Handler for deleting an employee
    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/"; // Redirect to the employee list page after deletion
    }
    // ... (existing code) ...

    // Handler to display the edit form for a specific employee
    @GetMapping("/editEmployee/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        // Fetch the employee by ID from the service
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "editEmployeeForm"; // Create a new Thymeleaf template for the edit form
    }

    // Handler for updating the employee after the edit form submission
    @PostMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute("employee") Employee editedEmployee) {
        // Update the employee using the EmployeeService
        employeeService.updateEmployee(id, editedEmployee);
        return "redirect:/"; // Redirect back to the home page after the update
    }

    // ... (existing code) ...
    @GetMapping("/downloadPDF")
    public ResponseEntity<ByteArrayResource> downloadPDF() {
        List<Employee> employees = employeeService.getAllEmployees();





        // Generate the PDF content from the list of employees
        byte[] pdfContent = generatePDFContent(employees);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "employees.pdf");

        // Return the PDF as a ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(pdfContent);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    private byte[] generatePDFContent(List<Employee> employees) {
        // Implement the logic to generate the PDF content using a PDF library (e.g., iText, Apache PDFBox).
        // You'll need to iterate through the list of employees and format them in the PDF.
        // Here's a simple example using iText library:
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Replace the following with your iText PDF generation logic
            // For simplicity, we'll just print the list of employees as plain text in the PDF.
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, baos);
            document.open();
            for (Employee employee : employees) {
                document.add(new com.itextpdf.text.Paragraph(employee.getEmail() + " - " + employee.getFirstName()+" - "+employee.getLastName()));
            }
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
