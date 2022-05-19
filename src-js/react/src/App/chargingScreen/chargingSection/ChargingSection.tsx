import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import { ThreeDots } from "react-loader-spinner";
import ErrorPage from "../../../components/error-page/ErrorPage";
import axios from "axios";

const url = `${process.env.REACT_APP_LINK_SERVE}charge/charging`;

const MainSection = () => {
  const [data, setData] = useState<any>(null);
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);

  useEffect(() => {
    setLoading(true);
    setInterval(() => {
      axios
        .get(url)
        .then((response) => {
          setData(response.data);
        })
        .catch((err) => {
          setError(err);
        })
        .finally(() => {
          setLoading(false);
        });
    }, 15000);
  }, [url]);

  if (loading)
    return (
      <div className={styles.load}>
        <ThreeDots color="#04AA6D" height={70} width={70} />
      </div>
    );

  if (error) return <ErrorPage />;

  console.log(data?.powerAgregation);

  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        <div>
          <Timer seconds={10} />
          <p>{data?.powerAgregation}</p>
        </div>
      </div>
    </div>
  );
};

export default MainSection;
