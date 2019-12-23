package com.xiken.projectantivenom;

public class PhoneUser {

        String userFirstName;
        String phoneNumber;
        String uid;

        public PhoneUser(String userFirstName, String phoneNumber,String uid) {
            this.userFirstName = userFirstName;
            this.phoneNumber = phoneNumber;
            this.uid =uid;
        }

        public PhoneUser() {
        }

        public String getUserFirstName() {
            return userFirstName;
        }

        public void setUserFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
        public String getUid(){
            return this.uid;
        }
        public void setUid(String uid){
            this.uid = uid;
        }

}
