package com.example.studyguide;

import java.util.ArrayList;

public class Markers {
private String Name;
private String gps;
private  int id;
private String desription;


    public Markers(String name, String gps, int id, String desription) {
        Name = name;
        this.gps = gps;
        this.id = id;
        this.desription = desription;
    }

    public Markers(int id, String name, String gps, String desription) {

        this.id = id;
        Name = name;
        this.gps = gps;
        this.desription=desription;

    }



    public Markers(String name, String  gps,String desription) {
        Name = name;
        this.gps = gps;
        this.desription=desription;
    }

    public Markers() {

    }



    public double splitLat() {
        String gps=getGps();
        String[] lon = gps.split(",");
        double latitude = Double.parseDouble(lon[0]);
        return latitude;

}

    public double splitLng()
    {
         String gps=getGps();
         String[] lon = gps.split(",");
         double longitude = Double.parseDouble(lon[1]);
         return longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /* private static int lastContactId = 0;

    public static ArrayList<Markers> createContactsList(int numContacts) {
        ArrayList<Markers> contacts = new ArrayList<Markers>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Markers("City " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }*/
}
