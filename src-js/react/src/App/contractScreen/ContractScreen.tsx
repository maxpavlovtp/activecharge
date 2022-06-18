import React, { useEffect } from "react";
import Header from "../../components/header/Header";
import ContractSection from "./contractSection/ContractSection";
import Footer from "./../../components/footer/Footer";
import styles from "./ContractScreen.module.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getDeviceIsOnStatus } from "../../store/reducers/ActionCreators";

const ContractScreen: React.FC = () => {
  const dispatch = useAppDispatch();
  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);
  useEffect(() => {
    dispatch(getDeviceIsOnStatus());
  }, [isDeviceOn]);
  return (
    <div className={styles.contractContainer}>
      <div>
        {isDeviceOn === true ? (
          <Header linkTo="/charging" />
        ) : (
          <Header linkTo="/" />
        )}
        <ContractSection />
      </div>
      <Footer />
    </div>
  );
};

export default ContractScreen;
