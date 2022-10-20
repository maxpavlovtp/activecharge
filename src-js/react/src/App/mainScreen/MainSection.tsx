import React, { useEffect, useState } from "react";
import "./MainSection.css";
import { Link, useOutletContext, useSearchParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import {
  getClientFingerPrint,
  getDeviceOnlineStatus,
  idStart,
} from "../../store/reducers/ActionCreators";
import MainImgLoadingLazy from "../../components/lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/chargingTiny.png";
import ErrorPage from "../../components/error-page/ErrorPage";
import axios from "axios";
import { Col, Container, Row } from "react-bootstrap";
import { useVisitorData } from "@fingerprintjs/fingerprintjs-pro-react";
import Spinner from "../../components/spinner/Spinner";

const MainSection: React.FC = () => {
  const [searchParams] = useSearchParams();
  // const [loading, setLoading] = useState<any>(true);
  const [errorPay, setErrorPay] = useState<any>(null);
  const [mainImgTheme] = useOutletContext<any>();
  const [payUrls, setPayUrls] = useState<any>([]);
  let stationNumber: any = searchParams.get("station");

  const clientFingerPrint = getClientFingerPrint();

  const { data } = useVisitorData();

  const { t } = useTranslation();

  const { errorCharging, errorStart, isDeviceOnline } = useAppSelector(
    (state) => state.fetchReducer
  );

  const dispatch = useAppDispatch();

  const startCharging = () => {
    dispatch(idStart(stationNumber));
  };

  useEffect(() => {
    dispatch(getDeviceOnlineStatus(stationNumber));
  }, []);

  let statusBtn = "btnStart";

  if (errorCharging) {
    return (
      <ErrorPage
        errorHeader={t("errorDevHeader")}
        errorBody={t("errorDevBody")}
      />
    );
  }

  if (errorStart || isDeviceOnline === false) {
    return (
      <ErrorPage
        errorHeader={t("errorOfflineHeader")}
        errorBody={t("errorOfflineBody")}
      />
    );
  }

  if (data) {
    console.log(data.visitorId);
  }

  if (isDeviceOnline === null) return <Spinner />;

  return (
    <Container fluid="lg">
      <Row className="justify-content-center">
        <h1 className="title">{t("title")}</h1>
      </Row>
      <Row className="justify-content-center mt-2">
        <p className="stationText">
          {t("station")}: <span className="stationNumber">{stationNumber}</span>
        </p>
      </Row>
      <Row className="justify-content-center mt-2 mb-5">
        <Col
          xs="3"
          sm="2"
          lg="2"
          as={Link}
          to={!errorStart && `/charging?station=${stationNumber}`}
          className="btnStart"
          onClick={startCharging}
        >
          {t("btns.startFree")}
        </Col>

        <Col
          as={Link}
          xs="2"
          sm="1"
          lg="1"
          className={`ml-2 ${statusBtn}`}
          to={`/payment?station=${stationNumber}&hours=${6}`}
          target="_blank"
          rel="noreferrer"
        >
          6{t("btns.start")}
        </Col>
        <Col
          as={Link}
          xs="3"
          sm="2"
          lg="2"
          className={`ml-2 ${statusBtn}`}
          to={`/payment?station=${stationNumber}&hours=${12}`}
          target="_blank"
          rel="noreferrer"
        >
          12{t("btns.start")}
        </Col>
      </Row>

      <Row className="justify-content-center">
        <MainImgLoadingLazy
          src={mainImgTheme}
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
