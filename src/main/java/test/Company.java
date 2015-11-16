package test;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

  @XmlElementWrapper(name = "computers")
  @XmlElements({
      @XmlElement(type = DesktopComputer.class, name = "desktop"),
      @XmlElement(type = LaptopComputer.class, name = "laptop")
  })
//  @JsonDeserialize(using = ComputerListDeserializer.class)
//  @JsonSerialize(using = ComputerListSerializer.class)
  private List<Computer> computers;

  public Company() {
    computers = new ArrayList<Computer>();
  }

  public List<Computer> getComputers() {
    return computers;
  }

  public void setComputers(List<Computer> computers) {
    this.computers = computers;
  }

  public Company addComputer(Computer computer) {
    if ( computers == null ) {
      computers = new ArrayList<Computer>();
    }
    computers.add(computer);
    return this;
  }
}
