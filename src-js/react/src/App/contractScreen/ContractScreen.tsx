import React from "react";
import ContractSection from "./contractSection/ContractSection";
import styles from "./ContractScreen.module.css";

const ContractScreen: React.FC = () => {
  return (
    <div className={styles.contractContainer}>
      <ContractSection />
    </div>
  );
};

export default ContractScreen;
