package com.mdnaimu0.json2java;

import java.util.List;

public class MyClass {
    private int id;
    private String name;
    private int roll;
    private double balance;
    private Address address;
    private Q q;
    private List<String> skills;
    private String date;
    private List<Projects> projects;
    private List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<Object>>>>>>>>>>>>>>>>>>>> ajaira_array;
    private List<List<Array>> array;

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return this.skills;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setQ(Q q) {
        this.q = q;
    }

    public Q getQ() {
        return this.q;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setProjects(List<Projects> projects) {
        this.projects = projects;
    }

    public List<Projects> getProjects() {
        return this.projects;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setArray(List<List<Array>> array) {
        this.array = array;
    }

    public List<List<Array>> getArray() {
        return this.array;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getRoll() {
        return this.roll;
    }

    public void setAjaira_array(List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<Object>>>>>>>>>>>>>>>>>>>> ajaira_array) {
        this.ajaira_array = ajaira_array;
    }

    public List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<List<Object>>>>>>>>>>>>>>>>>>>> getAjaira_array() {
        return this.ajaira_array;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public static class Array {
        private int field1;
        private String field2;
        private List<Field3> field3;

        public void setField1(int field1) {
            this.field1 = field1;
        }

        public int getField1() {
            return this.field1;
        }

        public void setField3(List<Field3> field3) {
            this.field3 = field3;
        }

        public List<Field3> getField3() {
            return this.field3;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public String getField2() {
            return this.field2;
        }

    }

    public static class Q {

    }

    public static class Field3 {
        private int field4;
        private List<String> field5;

        public void setField5(List<String> field5) {
            this.field5 = field5;
        }

        public List<String> getField5() {
            return this.field5;
        }

        public void setField4(int field4) {
            this.field4 = field4;
        }

        public int getField4() {
            return this.field4;
        }

    }

    public static class Address {
        private int code;
        private String city;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

    }

    public static class Address2 {
        private int code;
        private String city;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

    }

    public static class Projects {
        private String name;
        private String url;
        private Address2 address;
        private String link;

        public void setAddress(Address2 address) {
            this.address = address;
        }

        public Address2 getAddress() {
            return this.address;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

    }

}