package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.apache.struts2.ServletActionContext;
import task.entity.*;
import task.service.Manager;
import task.service.Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class OrganizationAction extends ActionSupport {

    private Integer id_org;
    private String orgname;
    private String country;
    private String city;
    private String street;
    private String house;
    private String zipcode;
    private String editStart;
    private Byte currStatus;
    private Integer id_cerf;

    private String formStart;
    private String formEnd;
    private Integer number;

    private Manager manager;
    private List<Organization> organizations;
    private File file;
    private String filename;

    private InputStream fileInputStream;
    private String fileNameExcel;

    private String DESTINATION_PATH_TO_FILES = "F:\\Andersen\\task1\\Temp";

    public String index() {
        if (id_org != null) {
            Organization organization = manager.getOrganizationByID(id_org);
            Address address = organization.getAddress();
            id_org = organization.getId();
            orgname = organization.getName();
            country = address.getCountry();
            city = address.getCity();
            street = address.getStreet();
            house = address.getHouse();
            zipcode = address.getZipCode();
        }
        return SUCCESS;
    }

    public String showOrgs() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer iDisplayStart = Integer.valueOf(request.getParameterMap().get("iDisplayStart")[0]);
        Integer iDisplayLength = Integer.valueOf(request.getParameterMap().get("iDisplayLength")[0]);
        int totalSize;

        String[] id_orgs = request.getParameterMap().get("id_org");
        if (id_orgs != null) {
            Integer orgId = Integer.valueOf(id_orgs[0]);
            organizations = manager.getOrganizationsWithoutID(orgId, iDisplayStart, iDisplayLength);
            totalSize = manager.getTotalCountOfOrganizations().intValue();
            totalSize--;
        } else {
            organizations = manager.getOrganizations(iDisplayStart, iDisplayLength);
            totalSize = manager.getTotalCountOfOrganizations().intValue();
        }
        Utils.toResponse(organizations, "jsonOrg", totalSize, totalSize);
        return SUCCESS;
    }

    public String showEmployees() {
        if (id_org != null) {
            List<JobPlace> employees = manager.getJobPlacesByOrganizationID(id_org);
            Utils.toResponse(employees, "jsonEmployees");
        }
        return SUCCESS;
    }

    public String showCertifications() {
        if (id_org != null) {
            List<CertificationView> certifications = manager.getCertificationsByOrganizationID(id_org);
            Utils.toResponse(certifications, "jsonCertifications");
        }
        return SUCCESS;
    }

    public String showForms() {
        List<Form> forms = manager.getFormsByOrganizationID(id_org);
        Utils.toResponse(forms, "jsonForms");
        return SUCCESS;
    }

    public String deleteLastCertification() {
        manager.deleteCertificationByID(id_cerf);
        return SUCCESS;
    }

    public String getCurrentStatus() {
        if (id_org != null) {
            CertificationView certification = manager.getCurrentCertificationByOrganizationID(id_org);
            currStatus = certification.getStatus();
        }
        return SUCCESS;
    }

    public String addOrg() {
        if (validation()) {
            return ERROR;
        }
        Organization organization = new Organization();
        Address address = new Address();
        if (id_org != null) {
            organization = manager.getOrganizationByID(id_org);
            address = organization.getAddress();
        }
        organization.setName(orgname);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setHouse(house);
        address.setZipCode(zipcode);
        organization.setAddress(address);
        organization.setEditStart(Date.valueOf(editStart));
        System.out.println(organization);
        manager.save(organization);
        return SUCCESS;
    }

    public String addForm() {
        Date startDate = Date.valueOf(formStart);
        Date endDate = Date.valueOf(formEnd);
        if (!Utils.checkDates(startDate, endDate)) {
            Date tmp;
            tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        Form form = new Form();
        form.setNumber(number);
        form.setStart(startDate);
        form.setEnd(endDate);
        form.setOrgId(id_org);
        manager.save(form);
        return SUCCESS;
    }

    public String transferForm() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer orgId = Integer.valueOf(request.getParameterMap().get("orgId")[0]);
        Integer formId = Integer.valueOf(request.getParameterMap().get("formId")[0]);
        Form form = manager.getFormByID(formId);
        form.setOrgId(orgId);
        manager.save(form);
        return SUCCESS;
    }

    public String uploadDocuments() {
        try {
            File destFile = new File(DESTINATION_PATH_TO_FILES, filename);
            FileUtils.copyFile(file, destFile);
            manager.addDocument(id_org, destFile.getAbsolutePath(), destFile);
        } catch (Exception e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String certify() {
        manager.addCertification(id_org);
        return SUCCESS;
    }

    public String refuse() {
        manager.refuseCertification(id_org);
        return SUCCESS;
    }

    public String remove() {
        manager.removeCertification(id_org);
        return SUCCESS;
    }

    public String delete() {
        manager.deleteOrgByID(Integer.valueOf(id_org));
        return SUCCESS;
    }

    private boolean validation() {
        return isStringEmpty(orgname)
                || isStringEmpty(country)
                || isStringEmpty(city)
                || isStringEmpty(street)
                || isStringEmpty(house)
                || isStringEmpty(zipcode);
    }

    private boolean isStringEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public String createXLSX() {
        organizations = manager.getAllOrganizations();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Organizations");
        XSSFRow header = spreadsheet.createRow(0);
        XSSFRow row;

        XSSFFont fontHeader = workbook.createFont();
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setFontHeightInPoints((short) 12);
        fontHeader.setColor(HSSFColor.WHITE.index);
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(fontHeader);
        headerStyle.setFillForegroundColor(HSSFColor.DARK_RED.index);
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        XSSFFont fontData = workbook.createFont();
        fontData.setFontName(HSSFFont.FONT_ARIAL);
        fontData.setFontHeightInPoints((short) 12);
        XSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFont(fontData);

        Cell cell;
        cell = header.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(1);
        cell.setCellValue("NAME");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(2);
        cell.setCellValue("COUNTRY");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(3);
        cell.setCellValue("CITY");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(4);
        cell.setCellValue("STREET");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(5);
        cell.setCellValue("BUILDING");
        cell.setCellStyle(headerStyle);
        cell = header.createCell(6);
        cell.setCellValue("ZIP CODE");
        cell.setCellStyle(headerStyle);

        for (int i = 0; i < organizations.size(); i++) {
            Organization organization = organizations.get(i);
            Address address = organization.getAddress();
            row = spreadsheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(organization.getId());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(1);
            cell.setCellValue(organization.getName());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(2);
            cell.setCellValue(address.getCountry());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(3);
            cell.setCellValue(address.getCity());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(4);
            cell.setCellValue(address.getStreet());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(5);
            cell.setCellValue(address.getHouse());
            cell.setCellStyle(dataStyle);
            cell = row.createCell(6);
            cell.setCellValue(address.getZipCode());
            cell.setCellStyle(dataStyle);
        }

        for (int i = 0; i < spreadsheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            spreadsheet.autoSizeColumn(i);
        }

        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DATE);
        String filename = DESTINATION_PATH_TO_FILES + "\\reports\\organizations_" + date + ".xlsx";
        File result = new File(filename);
        FileOutputStream out;

        try {
            out = new FileOutputStream(result);
            workbook.write(out);
            out.flush();
            out.close();

            fileNameExcel = result.getName();
            fileInputStream = new FileInputStream(result);
        } catch (IOException ignored) {
        }

        return SUCCESS;
    }

    public Integer getId_org() {
        return id_org;
    }

    public void setId_org(Integer id_org) {
        this.id_org = id_org;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEditStart() {
        return editStart;
    }

    public void setEditStart(String editStart) {
        this.editStart = editStart;
    }

    public void setId_cerf(Integer id_cerf) {
        this.id_cerf = id_cerf;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public Byte getCurrStatus() {
        return currStatus;
    }

    public void setUpload(File file) {
        this.file = file;
    }

    public void setUploadFileName(String filename) {
        this.filename = filename;
    }

    public void setFormStart(String formStart) {
        this.formStart = formStart;
    }

    public void setFormEnd(String formEnd) {
        this.formEnd = formEnd;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public String getFileNameExcel() {
        return fileNameExcel;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
