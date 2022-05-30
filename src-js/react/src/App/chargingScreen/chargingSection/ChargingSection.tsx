import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import { ThreeDots } from "react-loader-spinner";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<any>();
  const [response, setresponse] = useState<any>();
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);
  const url = `http://220-km.com:8080/on/start`;
  const urlChargingStatus = `http://220-km.com:8080/on/getChargingStatus`;

  const start = () => {
    setLoading(true);
    axios
      .get(url)
      .then((response) => {
        setMsg(response);
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setLoading(false);
      });
    console.log(msg);
  };

  const getCargingStatus = () => {
    setLoading(true);
    axios
      .get(urlChargingStatus)
      .then((response) => {
        setresponse(response.data.data  );
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setLoading(false);
      });
    console.log(response);
  }

  if (error) return <p>Error server!</p>;

  if (loading)
    return (
      <div className={styles.load}>
        <ThreeDots color="#04AA6D" height={70} width={70} />
      </div>
    );

  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        <button
          onClick={start}
          className={loading ? styles.disaleBtn : styles.btnPay}
        >
          start
        </button>
        <button
          onClick={getCargingStatus}
          className={loading ? styles.disaleBtn : styles.btnPay}
        >
          getChargingStatus
        </button>
        {msg?.data.message === "error" && (
          <ErrorPage
            errorHeader="Device is offline"
            errorBody="Sorry! Device is offline. Please, try later"
          />
        )}
        {msg?.data.message === "success" && <Timer seconds={10} />}
      </div>
    </div>
  );
};

export default MainSection;
