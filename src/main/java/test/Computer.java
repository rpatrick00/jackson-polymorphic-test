package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ LaptopComputer.class, DesktopComputer.class })
@XmlAccessorType(XmlAccessType.FIELD)
public class Computer {

  @XmlID
  @XmlAttribute
  private String id;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
}
