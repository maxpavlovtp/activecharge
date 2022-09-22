import React, { useState } from "react";
import "./MainSection.css";

import { Link, useOutletContext, useSearchParams } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { idStart } from "../../store/reducers/ActionCreators";
import MainImgLoadingLazy from "../../components/lazyLoading/MainImgLoadingLazy";
import placehoderSrc from "../../assets/chargingTiny.png";
import ErrorPage from "../../components/error-page/ErrorPage";
import axios from "axios";
import { Col, Container, Row } from "react-bootstrap";
import { setDeviceStatusUndefind } from "../../store/reducers/FetchSlice";
import { PayLinkLoading } from "../../components/stationCard/LoadingTime";

const MainSection: React.FC = () => {
  const [searchParams] = useSearchParams();
  const [errorPay, setErrorPay] = useState<any>(null);
  const [mainImgTheme] = useOutletContext<any>();

  const [loadingSixPayLink, setLoadingSixPayLink] = useState(false);
  const [loadingTwelvePayLink, setLoadingTwelvePayLink] = useState(false);

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
  // http://49.12.19.42:8080/
  // http://localhost:8080/

  const goPaySix = () => {
    setLoadingSixPayLink(true);
    try {
      axios.get(urlPayment6h).then((link) => {
        window.open(link.data.pageUrl, "_blank");
        setLoadingSixPayLink(false);
      });
    } catch (e: any) {
      setErrorPay(e.message);
    }
  };

  const goPayTwelve = () => {
    setLoadingTwelvePayLink(true);
    try {
      axios.get(urlPayment12h).then((link) => {
        window.open(link.data.pageUrl, "_blank");
        setLoadingTwelvePayLink(false);
      });
    } catch (e: any) {
      setErrorPay(e.message);
    }
  };

  let statusBtn = errorPay !== null ? "btnStart disableBtn" : "btnStart";

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
        <p className="stationText">
          {t("station")}: <span className="stationNumber">{stationNumber}</span>
        </p>
      </Row>
      <Row className="justify-content-center mt-2 mb-5">
        <Col
          xs="3"
          as={Link}
          to={`/charging?station=${stationNumber}`}
          className="btnStart"
          onClick={startCharging}
        >
          {t("btns.startFree")}
        </Col>

        <Col
          as={"a"}
          xs="3"
          className={`ml-2 ${statusBtn}`}
          onClick={goPaySix}
          target="_blank"
          rel="noreferrer"
        >
          {loadingSixPayLink === true ? (
            <PayLinkLoading />
          ) : (
            `6${t("btns.start")}`
          )}
        </Col>
        <Col
          as={"a"}
          xs="3"
          className={`ml-2 ${statusBtn}`}
          onClick={goPayTwelve}
          target="_blank"
          rel="noreferrer"
        >
          {loadingTwelvePayLink === true ? (
            <PayLinkLoading />
          ) : (
            `12${t("btns.start")}`
          )}
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
