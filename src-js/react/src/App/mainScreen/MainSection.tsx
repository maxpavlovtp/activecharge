import React, { useEffect, useState } from "react";
import "./MainSection.css";
import mainImg from "../../assets/charging.png";
import { Link, useSearchParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { idStart } from "../../store/reducers/ActionCreators";
import MainImgLoadingLazy from "../../components/lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/chargingTiny.png";
import ErrorPage from "../../components/error-page/ErrorPage";
import { setDeviceStatusUndefind } from "../../store/reducers/FetchSlice";
import axios from "axios";
import Spinner from "../../components/spinner/Spinner";
import { Col, Container, Row } from "react-bootstrap";

const MainSection: React.FC = () => {
  const [searchParams] = useSearchParams();
  const [payUrl12h, setPayUrl12h] = useState<any>(null);
  const [payUrl6h, setPayUrl6h] = useState<any>(null);
  let stationNumber: any = searchParams.get("station");
  const urlPayment12h = `${process.env.REACT_APP_LINK_SERVE}order/generateCheckoutLink?station_number=${stationNumber}&&hours=12`;
  const urlPayment6h = `${process.env.REACT_APP_LINK_SERVE}order/generateCheckoutLink?station_number=${stationNumber}&&hours=6`;

  const { t } = useTranslation();

  const { error } = useAppSelector((state) => state.fetchReducer);

  const dispatch = useAppDispatch();

  const startCharging = () => {
    dispatch(idStart(stationNumber));
    dispatch(setDeviceStatusUndefind(undefined));
  };

  let statusBtn = payUrl12h !== null || payUrl6h !== null ? "btnStart" : "btnStart disableBtn"

  useEffect(() => {
    try {
      axios.get(urlPayment12h).then(function (result: any) {
        setPayUrl12h(result?.data?.pageUrl);
        console.log(result.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);

  // todo use loop
  useEffect(() => {
    try {
      axios.get(urlPayment6h).then(function (result: any) {
        setPayUrl6h(result?.data?.pageUrl);
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
  return (
    <Container fluid="lg">
      <Row className="justify-content-center">
        <h1 className="title">{t("title")}</h1>
      </Row>
      <Row className="justify-content-center mt-2">
        <p className="stationText">Station: <span className="stationNumber">{stationNumber}</span></p>
      </Row>
      <Row className="justify-content-center mt-2 mb-5">
        <Col
          xs="auto"
          as={Link}
          to={`/charging?station=${stationNumber}`}
          className={statusBtn}
          onClick={startCharging}
        >
          {t("btns.startFree")}
        </Col>

        <Col
          as={"a"}
          xs="auto"
          className={`mr-3 ml-3 ${statusBtn}`}
          href={`${payUrl6h}`}
          target="_blank"
          rel="noreferrer"
        >
          {t("btns.start6h")}
        </Col>
        <Col
          xs="auto"
          as={"a"}
          className={statusBtn}
          href={`${payUrl12h}`}
          target="_blank"
          rel="noreferrer"
        >
          {t("btns.start")}
        </Col>
      </Row>
     
      <Row className="justify-content-center">
        <MainImgLoadingLazy
          src={mainImg}
          alt={"station"}
          placeholderSrc={placehoderSrc}
          width="256"
          heigth="256"
        />
      </Row>
    </Container>
  );
};

export default MainSection;
