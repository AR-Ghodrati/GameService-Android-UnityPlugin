package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import java.io.Serializable;

public class OBB implements Serializable {

    private String Name;
    private Long Size;

    public String getName () {
        return Name;
    }

    public void setName (String name) {
        Name = name;
    }

    public Long getSize () {
        return Size;
    }

    public void setSize (Long size) {
        Size = size;
    }
}
