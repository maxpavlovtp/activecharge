import React from 'react';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import StatisticSection from './statisticSection/StatisticSection';
import styles from './StatisticScreen.module.css';

const ContactsScreen: React.FC = () => {
  return (
    <div className={styles.contactsContainer}>
      <div>
        <Header />
        <StatisticSection />
      </div>
      <Footer />
    </div>
  );
};

export default ContactsScreen;
