import React, { useEffect, useState } from "react";
import styles from "./OverloadPage.module.css";
import axios, { AxiosResponse } from "axios";
import Header from "../header/Header";
import Footer from "../footer/Footer";

const OverloadPage = () => {
  const [power, setPower] = useState<any>();
  const [link, setLink] = useState<any>();
  const [overload, setOverload] = useState<any>(false);
  const [completed, setCompleted] = useState<any>(false);
  const [loading, setLoading] = useState<any>(true);
  const [error, setError] = useState<any>(null);

  const urlPowerLimit = `${process.env.REACT_APP_LINK_SERVE}device/getPowerLimit`;
  const urlPowerDevice = `${process.env.REACT_APP_LINK_SERVE}device/getPower`;
  const urlCheckCompleted = `${process.env.REACT_APP_LINK_SERVE}device/isOverloadCheckCompleted`;
  const urlIsOverloaded = `${process.env.REACT_APP_LINK_SERVE}device/isPowerLimitOvelrloaded`;
  const urlPayment = `http://220-km.com:5000`;

  let powerLimit: AxiosResponse;
  const getPowerLimit = () => {
    axios
      .get(urlPowerLimit)
      .then((response) => {
        powerLimit = response.data.data;
        // console.log(powerLimit.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const isOverloaded = () => {
    axios
      .get(urlIsOverloaded)
      .then((response) => {
        setOverload(response.data.data);
        console.log(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const isCheckedCompleted = () => {
    axios
      .get(urlCheckCompleted)
      .then((response) => {
        setCompleted(response.data.data);
        console.log(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const getPower = () => {
    axios
      .get(urlPowerDevice)
      .then((response) => {
        setPower(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
    console.log(power);
  };

  const checkPowerLimit = () => {
    if (overload === false && completed === false) {
      getPower();
      isOverloaded();
      isCheckedCompleted();
    }
    if (overload === true || completed === true) {
      return;
    }
  };

  const getPayLink = async () => {
    if (completed && link === undefined) {
      setLoading(true);
      axios
        .get(urlPayment)
        .then((response) => {
          setLink(response.data.message);
          console.log(response.data.message);
        })
        .catch((err) => {
          setError(err);
        })
        .finally(() => {
          setLoading(false);
        });
      console.log(link);
    }
  };

  useEffect(() => {
    getPowerLimit();
    const overloadTimer = setInterval(() => {
      checkPowerLimit();
      getPayLink();
    }, 1000);
    return () => clearInterval(overloadTimer);
  });

  return (
    <div className={styles.container}>
      <Header />
      <div className={styles.checkOverloadContainer}>
        {/* <div className={styles.overloadBox}> */}
        {completed === true ? (
          <div className={styles.seccessCont}>
            {loading ? (
              <div className={styles.seccessCont}>
                <p className={styles.waitPay}>Wait a second! </p>
                <p className={styles.waitPayTwo}>Getting payment link</p>
              </div>
            ) : (
              <div className={styles.seccessCont}>
                <button
                  className={loading ? styles.disaleBtn : styles.btnPay}
                  onClick={() => window.open(link)}
                  // onClick={start}
                >
                  Pay
                </button>
                <p className={styles.successCheck}>Success!</p>
                <p className={styles.successCheck}>Let`s charge it!</p>
              </div>
            )}
          </div>
        ) : (
          <div className={styles.checkContainer}>
            <p className={styles.checkText}>is overloaded checking</p>
          </div>
        )}
        {overload === true && (
          <div>
            <p>please, slow down and try again</p>
            <button
              onClick={() => {
                window.location.reload();
                setOverload(false);
              }}
            >
              Try Again
            </button>
          </div>
        )}
        {/* </div> */}
      </div>

      <Footer />
    </div>
  );
};

export default OverloadPage;
