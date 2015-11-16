package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "desktop")
public class DesktopComputer extends Computer {

  @XmlElement(name = "location")
  private String location;

  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
}
