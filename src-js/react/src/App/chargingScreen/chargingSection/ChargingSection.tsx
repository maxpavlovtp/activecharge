import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import { IoEarthOutline } from "react-icons/io5";

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<any>();
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);
  const url = `http://220-km.com:8080/device/start`;
  const { t } = useTranslation();

  const start = () => {
    setLoading(true);
    axios
      .get(url)
      .then((response) => {
        setMsg(response);
        console.log(response);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  if (error)
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );

  if (loading)
    return (
      <div className={styles.load_animation}>
        <IoEarthOutline className={styles.animation}/>
      </div>
    );

  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        <button
          onClick={start}
          className={loading ? styles.disaleBtn : styles.btnPay}
        >
          {t("btns.start")}
        </button>
        {msg?.data.message === "success" && <Timer seconds={10} />}
        {msg?.data?.message === "error" && (
          <ErrorPage
            errorHeader={t("errorHeader")}
            errorBody={t("errorBody")}
          />
        )}
      </div>
    </div>
  );
};

export default MainSection;
