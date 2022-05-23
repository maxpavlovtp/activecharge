import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import { ThreeDots } from "react-loader-spinner";
import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<any>();
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);
  const url = `${process.env.REACT_APP_LINK_SERVE}charge/charging`;

  useEffect(() => {
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
  }, []);
  console.log(msg);

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
        {msg?.data.message === "error" && <ErrorPage numError='500' errorHeader='Device is offline' errorBody='Sorry! Device is offline now. Please, try later'/>}
        {msg?.data.message === "success" && <Timer seconds={10} />}
      </div>
    </div>
  );
};

export default MainSection;
