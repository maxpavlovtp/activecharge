import React, { useEffect, useState } from "react";
import styles from "./OverloadPage.module.css";
import axios, { AxiosResponse } from "axios";
import Header from "../header/Header";
import Footer from "../footer/Footer";
import { useAppSelector } from "../../hooks/reduxHooks";
import { useAppDispatch } from "../../hooks/reduxHooks";
import ErrorPage from "../error-page/ErrorPage";
import { t } from "i18next";
import Spinner from "../spinner/Spinner";
import { fetchOverloadData } from "../../store/reducers/ActionCreators";

const OverloadPage = () => {
  const [power, setPower] = useState<any>();
  const [link, setLink] = useState<any>();
  const [overload, setOverload] = useState<any>(false);
  const [completed, setCompleted] = useState<any>(false);
  const [loading, setLoading] = useState<any>(true);

  const { isLoadingOverload, error } = useAppSelector(
    (state) => state.fetchReducer
  );

  const urlPowerDevice = `${process.env.REACT_APP_LINK_SERVE}device/getPower`;
  const urlCheckCompleted = `${process.env.REACT_APP_LINK_SERVE}device/isOverloadCheckCompleted`;
  const urlIsOverloaded = `${process.env.REACT_APP_LINK_SERVE}device/isPowerLimitOvelrloaded`;
  const urlPayment = `http://220-km.com:5000`;

  const dispatch = useAppDispatch();

  const startOverloadChecing = () => {
    dispatch(fetchOverloadData());
  };

  const isOverloaded = () => {
    axios
      .get(urlIsOverloaded)
      .then((response) => {
        setOverload(response.data.data);
        console.log(response.data.data);
      })
      .catch((err) => {
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
        console.log(err);
      });
    console.log(power);
  };

  const checkPowerLimit = () => {
    if (overload === false && completed === false) {
      getPower();
      isOverloaded();
      isCheckedCompleted();
    } else if (overload === true || completed === true) {
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
          console.log(err);
        })
        .finally(() => {
          setLoading(false);
        });
      console.log(link);
    }
  };

  useEffect(() => {
    console.log(isLoadingOverload);
    if (isLoadingOverload === false) {
      getPayLink();
      const overloadTimer = setInterval(() => {
        checkPowerLimit();
      }, 1000);
      return () => clearInterval(overloadTimer);
    }
  });

  if (error) {
    console.log(error);
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );
  }

  if (isLoadingOverload === true) return <Spinner />;

  return (
    <div>
      {isLoadingOverload === false && (
        <div className={styles.checkOverloadContainer}>
          {overload === false && (
            <>
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
            </>
          )}

          {overload === true && (
            <div className={styles.seccessCont}>
              <p className={styles.checkTextOverload}>
                Overload! <br />
                Please, slow down and try again
              </p>
              <button
                className={styles.btnPayOverload}
                onClick={() => {
                  startOverloadChecing();
                  setOverload(false);
                  setCompleted(false);
                }}
              >
                Try Again
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default OverloadPage;
