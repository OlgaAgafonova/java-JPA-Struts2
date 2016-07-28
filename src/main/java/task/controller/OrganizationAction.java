package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.Address;
import task.entity.Organization;
import task.service.Manager;
import task.service.Utils;

import java.util.List;

public class OrganizationAction extends ActionSupport {

    private String id;
    private String orgname;
    private String country;
    private String city;
    private String street;
    private String house;
    private String zipcode;

    private Manager manager;
    private List organizations;

    public String index() {
        if (id != null && !id.trim().isEmpty()) {
            Organization organization = manager.getOrganizationByID(Integer.valueOf(id));
            Address address = organization.getAddress();
            id = String.valueOf(organization.getId());
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
        organizations = manager.getAllOrganizations();
        Utils.toResponse(organizations, "jsonOrg");
        return SUCCESS;
    }

    public String addOrg() {
        if (validation()) {
            return ERROR;
        }
        Organization organization = new Organization();
        Address address = new Address();
        if (id != null && !id.trim().isEmpty()) {
            organization = manager.getOrganizationByID(Integer.valueOf(id));
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

    public String delete() {
        manager.deleteOrgByID(Integer.valueOf(id));
        return SUCCESS;
    }

    public String employees() {
        if (id != null && !id.trim().isEmpty()) {
            List employees = manager.getJobPlacesByOrganizationID(Integer.valueOf(id));
            Utils.toResponse(employees, "jsonEmployees");
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
