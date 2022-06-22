import React from "react";
import ChargingSection from "./chargingSection/ChargingSection";
import styles from "./ChargingScreen.module.css";

const ChargingScreen: React.FC = () => {
  return (
    <div className={styles.chargingContainer}>
      <ChargingSection />
    </div>
  );
};

export default ChargingScreen;
