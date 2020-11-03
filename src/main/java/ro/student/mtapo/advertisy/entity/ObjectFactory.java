//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.03 at 09:51:49 PM EET 
//


package ro.student.mtapo.advertisy.entity;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ro.student.mtapo.advertisy.entity.copy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ro.student.mtapo.advertisy.entity.copy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Announce }
     * 
     */
    public Announce createAnnounce() {
        return new Announce();
    }

    /**
     * Create an instance of {@link AnnounceFile }
     * 
     */
    public AnnounceFile createAnnounceFile() {
        return new AnnounceFile();
    }

    /**
     * Create an instance of {@link AnnounceUser }
     * 
     */
    public AnnounceUser createAnnounceUser() {
        return new AnnounceUser();
    }

    /**
     * Create an instance of {@link BannedAnnounce }
     * 
     */
    public BannedAnnounce createBannedAnnounce() {
        return new BannedAnnounce();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link City }
     * 
     */
    public City createCity() {
        return new City();
    }

    /**
     * Create an instance of {@link Country }
     * 
     */
    public Country createCountry() {
        return new Country();
    }

    /**
     * Create an instance of {@link File }
     * 
     */
    public File createFile() {
        return new File();
    }

    /**
     * Create an instance of {@link ReportedAnnounce }
     * 
     */
    public ReportedAnnounce createReportedAnnounce() {
        return new ReportedAnnounce();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link UnbanAnnounceReq }
     * 
     */
    public UnbanAnnounceReq createUnbanAnnounceReq() {
        return new UnbanAnnounceReq();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

}