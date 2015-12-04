package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.domain.Organisation;
import de.helfenkannjeder.come2help.server.service.OrganisationService;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/organisations")
@Transactional
public class OrganisationController {

    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiErrors(apierrors = {@ApiError(code = "500", description = "Internal Server Error")})
    public Organisation getOrganisationById(@PathVariable(value = "id") Long id) {
        Organisation organisation = organisationService.findById(id);
        return organisation;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrors(apierrors = {@ApiError(code = "400", description = "Bad Request"), @ApiError(code = "500", description = "Internal Server Error")})
    public Organisation createOrganisation(@Valid @RequestBody Organisation organisation) {
        Organisation createdOrganisation = organisationService.createOrganisation(organisation);

        return createdOrganisation;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ApiErrors(apierrors = {@ApiError(code = "400", description = "Bad Request"), @ApiError(code = "500", description = "Internal Server Error")})
    public Organisation updateOrganisation(@Valid @RequestBody Organisation organisation) {
        Organisation updatedOrganisation = organisationService.updateOrganisation(organisation);

        return updatedOrganisation;
    }
}
