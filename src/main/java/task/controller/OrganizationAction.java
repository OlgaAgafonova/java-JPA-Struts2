package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import task.entity.Address;
import task.entity.Certification;
import task.entity.JobPlace;
import task.entity.Organization;
import task.service.Manager;
import task.service.Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrganizationAction extends ActionSupport {

    private Integer id_org;
    private String orgname;
    private String country;
    private String city;
    private String street;
    private String house;
    private String zipcode;
    private Byte currStatus;

    private Manager manager;
    private List<Organization> organizations;
    private File file;
    private String filename;

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

        organizations = manager.getOrganizations(iDisplayStart, iDisplayLength);
        int totalSize = manager.getTotalCountOfOrganizations().intValue();
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
            List<Certification> certifications = manager.getCertificationsByOrganizationID(id_org);
            Utils.toResponse(certifications, "jsonCertifications");
        }
        return SUCCESS;
    }

    public String getCurrentStatus() {
        if (id_org != null) {
            Certification certification = manager.getCurrentCertificationByOrganizationID(id_org);
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
        manager.save(organization);
        return SUCCESS;
    }

    public String uploadDocuments() {
        String destPath = "F:\\Andersen\\task1\\Temp";

        try {
            File destFile = new File(destPath, filename);
            FileUtils.copyFile(file, destFile);
            manager.addDocument(id_org, destFile.getAbsolutePath(), destFile);
        } catch (IOException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public String delete() {
        manager.deleteOrgByID(Integer.valueOf(id_org));
        return SUCCESS;
    }

    private boolean validation() {
        if (isStringEmpty(orgname)) {
            return true;
        }
        if (isStringEmpty(country)) {
            return true;
        }
        if (isStringEmpty(city)) {
            return true;
        }
        if (isStringEmpty(street)) {
            return true;
        }
        if (isStringEmpty(house)) {
            return true;
        }
        if (isStringEmpty(zipcode)) {
            return true;
        }
        return false;
    }

    private boolean isStringEmpty(String string) {
        if (string == null || string.trim().isEmpty()) {
            return true;
        }
        return false;
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

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public Byte getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(Byte currStatus) {
        this.currStatus = currStatus;
    }

    public void setUpload(File file) {
        this.file = file;
    }

    public void setUploadFileName(String filename) {
        this.filename = filename;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
