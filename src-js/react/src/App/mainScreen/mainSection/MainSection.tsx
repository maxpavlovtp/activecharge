import React, { useEffect, useState } from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import { Link } from "react-router-dom";

import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";

const MainSection: React.FC = () => {
  const [link, setLink] = useState<any>();
  const [loading, setLoading] = useState<any>(true);
  const [error, setError] = useState<any>(null);

  const url = `${process.env.REACT_APP_LINK_SERVE}`;

  useEffect(() => {
    axios
      .get(url)
      .then((response) => {
        setLink(response.data.message);
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);
  console.log(link);

  if (error) {
    console.log(error.message);
    return <p>Error server!</p>;
  }

  if (loading)
    return (
      <></>
    );

  return (
    <>
      <div className={styles.mainBox}>
        {link !== "error" ? (
          <div className={styles.container}>
            <h1 className={styles.title}>Заряди 220 кілометрів за ніч</h1>
            <div className={styles.btnStart}>
              <button
                disabled={loading ? true : false}
                className={loading ? styles.disaleBtn : styles.btnPay}
                onClick={() => window.open(link)}
              >
                Start
              </button>
              <Link className={styles.btn} to="/charging">
                Start Free
              </Link>
            </div>
            <div className={styles.imgCont}>
              <img className={styles.mainImg} src={mainImg} alt="mainImg" />
            </div>
          </div>
        ) : (
          <ErrorPage
            errorHeader="Device is offline"
            errorBody="Sorry! Device is offline. Please, try later"
          />
        )}
      </div>
    </>
  );
};

export default MainSection;
