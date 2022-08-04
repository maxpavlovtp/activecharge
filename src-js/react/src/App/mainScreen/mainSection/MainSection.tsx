import React, { useEffect, useState } from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import { Link, useSearchParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useAppDispatch, useAppSelector } from "../../../hooks/reduxHooks";
import { idStart } from "../../../store/reducers/ActionCreators";
import MainImgLoadingLazy from "../../../components/lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../../assets/chargingTiny.png";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { setDeviceStatusUndefind } from "../../../store/reducers/FetchSlice";
import axios from "axios";
import Spinner from "../../../components/spinner/Spinner";

const MainSection: React.FC = () => {
  const [searchParams] = useSearchParams();
  const [payUrl, setPayUrl] = useState<any>(null)
  let stationNumber: any = searchParams.get("station");
  const urlPayment = `${process.env.REACT_APP_LINK_SERVE}order/generateCheckoutLink?station_number=${stationNumber}`;

  const { t } = useTranslation();

  const { error } = useAppSelector((state) => state.fetchReducer);

  const dispatch = useAppDispatch();

  const startCharging = () => {
    dispatch(idStart(stationNumber));
    dispatch(setDeviceStatusUndefind(undefined));
  };

  useEffect(() => {
    try {
      axios.get(urlPayment).then(function (result: any) {
        setPayUrl(result?.data?.pageUrl)
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

  if (payUrl === null) {
    return <Spinner />
  }

  let withStartBtn = (
    <>
      <div className={styles.mainBox}>
        <div className={styles.container}>
          <h1 className={styles.title}>{t("title")}</h1>
          <div className={styles.btnStart}>
            <a
              className={styles.btnPay}
              href={`${payUrl}`}
              target="_blank"
              rel="noreferrer"
            >
              {t("btns.start")}
            </a>

            <Link
              to={`/charging?station=${stationNumber}`}
              className={styles.btn}
              onClick={startCharging}
            >
              {t("btns.startFree")}
            </Link>
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
  let withoutStartBtn = (
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

  return process.env.REACT_APP_LINK_SERVE === "http://220-km.com:8080/"
    ? withoutStartBtn
    : withStartBtn;
};

export default MainSection;
