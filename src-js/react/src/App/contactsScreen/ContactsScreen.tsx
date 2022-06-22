import React from "react";
import ContactsSection from "./contactsSection/ContactsSection";
import styles from "./ContactsScreen.module.css";

const ContactsScreen: React.FC = () => {
  return (
    <div className={styles.contactsContainer}>
      <ContactsSection />
    </div>
  );
};

export default ContactsScreen;
