import React, {useEffect, useState} from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import {Link, useSearchParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useAppDispatch, useAppSelector} from "../../../hooks/reduxHooks";
import {idStart} from "../../../store/reducers/ActionCreators";
import MainImgLoadingLazy from "../../../components/lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../../assets/chargingTiny.png";
import ErrorPage from "../../../components/error-page/ErrorPage";
import {setDeviceStatusUndefind} from "../../../store/reducers/FetchSlice";
import axios from "axios";
import Spinner from "../../../components/spinner/Spinner";

const MainSection: React.FC = () => {
  const [searchParams] = useSearchParams();
  const [payUrl12h, setPayUrl12h] = useState<any>(null)
  const [payUrl6h, setPayUrl6h] = useState<any>(null)
  let stationNumber: any = searchParams.get("station");
  const urlPayment12h = `${process.env.REACT_APP_LINK_SERVE}order/generateCheckoutLink?station_number=${stationNumber}&&hours=12`;
  const urlPayment6h = `${process.env.REACT_APP_LINK_SERVE}order/generateCheckoutLink?station_number=${stationNumber}&&hours=6`;

  const { t } = useTranslation();

  const { error } = useAppSelector((state) => state.fetchReducer);

  const dispatch = useAppDispatch();

  const startCharging = () => {
    dispatch(idStart(stationNumber));
    dispatch(setDeviceStatusUndefind(undefined));
  };

  useEffect(() => {
    try {
      axios.get(urlPayment12h).then(function (result: any) {
        setPayUrl12h(result?.data?.pageUrl)
        console.log(result.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);

  //todo use loop
  useEffect(() => {
    try {
      axios.get(urlPayment6h).then(function (result: any) {
        setPayUrl6h(result?.data?.pageUrl)
        console.log(result.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);

  if (error) {
    return (
      <ErrorPage
        errorHeader={t("errorDevHeader")}
        errorBody={t("errorDevBody")}
      />
    );
  }

  if (payUrl12h === null || payUrl6h === null ) {
    return <Spinner />
  }

  return (
      <>
        <div className={styles.mainBox}>
          <div className={styles.container}>
            <h1 className={styles.title}>{t("title")}</h1>
            <div className={styles.btnStart}>
              <Link
                  to={`/charging?station=${stationNumber}`}
                  className={styles.btn}
                  onClick={startCharging}
              >
                {t("btns.startFree")}
              </Link>

              <a
                  className={styles.btnPay}
                  href={`${payUrl6h}`}
                  target="_blank"
                  rel="noreferrer"
              >
                {t("btns.start6h")}
              </a>
              <a
                  className={styles.btnPay}
                  href={`${payUrl12h}`}
                  target="_blank"
                  rel="noreferrer"
              >
                {t("btns.start")}
              </a>
            </div>
            <div className={styles.imgCont}>
              <MainImgLoadingLazy
                  src={mainImg}
                  alt={"station"}
                  placeholderSrc={placehoderSrc}
                  width="256"
                  heigth="256"
              />
            </div>
          </div>
        </div>
      </>
  );
};

export default MainSection;
