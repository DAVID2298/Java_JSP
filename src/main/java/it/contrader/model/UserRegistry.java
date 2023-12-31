package it.contrader.model;

public class UserRegistry {

        private int id;

        private String name;

        private String address;

        private String surname;

        private String birthDate;

        private int user_id;

        public UserRegistry(int id, String name, String address, String surname, String birthDate, int user_id) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.surname = surname;
            this.birthDate = birthDate;
            this.user_id = user_id;
        }

        public UserRegistry(String name, String address, String surname, String birthDate) {
            this.name = name;
            this.address = address;
            this.surname = surname;
            this.birthDate = birthDate;
        }

        public UserRegistry(String name, String address, String surname, String birthDate, int user_id) {
            this.name = name;
            this.address = address;
            this.surname = surname;
            this.birthDate = birthDate;
            this.user_id=user_id;
        }


        public UserRegistry() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "UserRegistry{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", surname='" + surname + '\'' +
                    ", birthDate='" + birthDate + '\'' +
                    ", user_id=" + user_id +
                    '}';
        }
    }


