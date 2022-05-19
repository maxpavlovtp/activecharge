import { useEffect, useState } from "react";
import styles from "./ChargingSection.module.css";
import Timer from "../../../components/timer/Timer";
import { ThreeDots } from "react-loader-spinner";
import ErrorPage from "../../../components/error-page/ErrorPage";
import axios from "axios";

const url = `${process.env.REACT_APP_LINK_SERVE}charge/charging`;

const MainSection = () => {
  const [data, setData] = useState<any>([0, 0]);
  const [loading, setLoading] = useState<any>(false);
  const [error, setError] = useState<any>(null);

  useEffect(() => {
    setLoading(true);
    setInterval(() => {
      axios
        .get(url)
        .then((response) => {
          setData((oldArr: any) => [
            ...oldArr,
            Number(response.data.powerAgregation),
          ]);
        })
        .catch((err) => {
          setError(err);
        })
        .finally(() => {
          setLoading(false);
        });
    }, 6000);
  }, [url]);

  if (loading)
    return (
      <div className={styles.load}>
        <ThreeDots color="#04AA6D" height={70} width={70} />
      </div>
    );

  if (error) return <ErrorPage />;
  
  if (data) console.log(data);

  return (
    <div className={styles.chargingBox}>
      <div className={styles.contTimer}>
        <div>
          <Timer seconds={10} />
          <p>{data.reduce((prevValue: number, currValue: number) => prevValue + currValue).toFixed(2)}</p>
        </div>
      </div>
    </div>
  );
};

export default MainSection;
