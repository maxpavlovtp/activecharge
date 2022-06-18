import React, { useEffect } from "react";
import Header from "../../components/header/Header";
import Footer from "./../../components/footer/Footer";
import ContactsSection from "./contactsSection/ContactsSection";
import styles from "./ContactsScreen.module.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getDeviceIsOnStatus } from "../../store/reducers/ActionCreators";

const ContactsScreen: React.FC = () => {
  const dispatch = useAppDispatch();
  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);
  useEffect(() => {
    dispatch(getDeviceIsOnStatus());
  }, [isDeviceOn]);

  return (
    <div className={styles.contactsContainer}>
      <div>
        {isDeviceOn === true ? (
          <Header linkTo="/charging" />
        ) : (
          <Header linkTo="/" />
        )}
        <ContactsSection />
      </div>
      <Footer />
    </div>
  );
};

export default ContactsScreen;
