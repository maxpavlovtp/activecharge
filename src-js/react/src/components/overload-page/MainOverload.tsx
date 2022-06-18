import Header from "../../components/header/Header";
import styles from '../../App/mainScreen/Main.module.css'
import Footer from "../../components/footer/Footer";
import OverloadPage from "./OverloadPage";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { useEffect } from "react";
import { getDeviceIsOnStatus } from "../../store/reducers/ActionCreators";

function HomeScreen() {
  const dispatch = useAppDispatch();
  const { isDeviceOn } = useAppSelector((state) => state.fetchReducer);
  useEffect(() => {
    dispatch(getDeviceIsOnStatus());
  }, [isDeviceOn]);
  return (
    <div className={styles.mainContainer}>
      {isDeviceOn === true ? (
          <Header linkTo="/overload" />
        ) : (
          <Header linkTo="/" />
        )}
      <OverloadPage />
      <Footer/>
    </div>

  );
}

export default HomeScreen;
