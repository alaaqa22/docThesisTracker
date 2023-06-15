package dtt.business.backing;

import dtt.global.utilities.ConfigReader;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;


@ViewScoped
@Named
public class ImpressumBacking implements Serializable {
    private String impressumContent;

    public ImpressumBacking() {
        System.out.println ("1");
        if(!ConfigReader.arePropertiesLoaded()){
            ConfigReader.loadProperties();
        }
        impressumContent = ConfigReader.getImpressumContent();
        System.out.println ("2");
    }


    public String getImpressumContent() {
        return impressumContent;
    }

    public void setImpressumContent(String impressumContent) {
        this.impressumContent = impressumContent;
    }
}
