import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../../components/spinner/Spinner";

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

  useEffect(() => {
    start();
  }, []);

  if (error)
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );

  if (loading)
    return (
      <Spinner />
    );

  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        {msg?.data.message === "success" && <Timer minutes={15} />}
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
