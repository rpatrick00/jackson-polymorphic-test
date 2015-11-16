package test;

import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RunThis {

  private static final String JAXB_OUTPUT_FILE = "company-jaxb.xml";
  private static final String JACKSON_OUTPUT_FILE = "company-jackson.xml";

  private XmlMapper _mapper = null;
  private Company company;

  public RunThis() {
    _mapper = initXmlMapper();
    Company company = new Company();

    DesktopComputer computer1 = new DesktopComputer();
    computer1.setId("computer-1");
    computer1.setLocation("Bangkok");
    company.addComputer(computer1);

    DesktopComputer computer2 = new DesktopComputer();
    computer2.setId("computer-2");
    computer2.setLocation("Pattaya");
    company.addComputer(computer2);

    LaptopComputer computer3 = new LaptopComputer();
    computer3.setId("computer-3");
    computer3.setVendor("Apple");
    company.addComputer(computer3);
    this.company = company;
  }

  public Company getCompany() {
    return company;
  }

  public void writeToStdOut(Company company) throws JAXBException, IOException {
    System.out.println("\n\nUsing JAXB:\n\n");
    writeJAXB(System.out, company);
    System.out.println("\n\nUsing Jackson:\n\n");
    writeJackson(System.out, company);
  }

  public void writeToFile(Company company) throws JAXBException, IOException {
    System.out.println("Using JAXB to write " + JAXB_OUTPUT_FILE);
    FileOutputStream fos = new FileOutputStream(new File(JAXB_OUTPUT_FILE), false);
    writeJAXB(fos, company);
    fos.close();

    System.out.println("Using Jackson to write " + JACKSON_OUTPUT_FILE);
    fos = new FileOutputStream(new File(JACKSON_OUTPUT_FILE), false);
    writeJAXB(fos, company);
    fos.close();
  }

  public Company readJaxbFile() throws JAXBException, IOException {
    JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    Company jaxbCo = (Company) jaxbUnmarshaller.unmarshal(new File(JAXB_OUTPUT_FILE));
    return jaxbCo;
  }

  public Company readJacksonFile() throws IOException {
    ObjectMapper mapper = getXmlMapper();
    Company jacksonCo = mapper.readValue(new File(JACKSON_OUTPUT_FILE), Company.class);
    return jacksonCo;
  }

  public XmlMapper getXmlMapper() {
    return _mapper;
  }

  private void writeJAXB(OutputStream out, Company xml) throws JAXBException, IOException {
    JAXBContext jc = JAXBContext.newInstance(Company.class);
    Marshaller marshaller = jc.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal(xml, out);
  }

  private void writeJackson(OutputStream out, Company xml) throws JAXBException, IOException {
    ObjectMapper mapper = getXmlMapper();
    mapper.writeValue(out, xml);
  }

  private XmlMapper initXmlMapper() {
    //    XmlMapper mapper = new CustomXmlMapper(inFactory, outFactory);
    //    XmlMapper mapper = new XmlMapper(inFactory, outFactory);

    //    XMLInputFactory inFactory = new WstxInputFactory();
    //    XMLOutputFactory2 outFactory = new WstxOutputFactory();
    //    outFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);

    XmlMapper mapper = new XmlMapper();
    //      mapper = new CustomXmlMapper();
    //      mapper = new CustomXmlMapper(inFactory, outFactory);

    mapper.registerModule(new JaxbAnnotationModule());
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
    return mapper;
  }

  public static void main(String[] args) throws Exception {
    RunThis me = new RunThis();
    Company c = me.getCompany();
//    me.writeToStdOut(c);
    me.writeToFile(c);

    System.out.println("Using JAXB to read the file previous written by JAXB:");
    c = me.readJaxbFile();
    me.writeJAXB(System.out, c);


    System.out.println("Using Jackson to read the file previously written by Jackson:");
    c = me.readJacksonFile();
//    me.writeJackson(System.out, c);
  }
}
