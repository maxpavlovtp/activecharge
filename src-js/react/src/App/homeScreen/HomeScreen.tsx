import axios from "axios";
import React, { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useSearchParams } from "react-router-dom";
import ErrorPage from "../../components/error-page/ErrorPage";
import Spinner from "../../components/spinner/Spinner";
import StationCard from "../../components/stationCard/StationCard";
import { useAppDispatch } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";

const urlV2StatusAll = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/statusAll`;

export default function HomeScreen() {
  const [loading, setLoading] = useState<any>(true);
  const [statusALl, setStatusAll] = useState<any>();
  const [searchParams] = useSearchParams();
  let stationNumber: any = searchParams.get("station");
  const interval: any = localStorage.getItem("interval");
  const sec = 5000;

  const { t } = useTranslation();

  useEffect(() => {
    try {
      axios.get(urlV2StatusAll).then(function (result: any) {
        setStatusAll(result.data);
        statusALl !== null && setLoading(false);
        console.log(result?.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
    const timerID = setInterval(() => {
      try {
        axios.get(urlV2StatusAll).then(function (result: any) {
          setStatusAll(result.data);
          statusALl !== null && setLoading(false);
          console.log(result?.data);
        });
      } catch (e: any) {
        console.log(e.message);
      }
    }, sec);
    return () => clearInterval(timerID);
  }, []);

  if (loading === true) return <Spinner />;

  return (
    <Container>
      <Row className="justify-content-center">
        {statusALl?.map((data: any) => {
          if (data) {
            return (
              <Col lg={4} md={6} key={Math.random()}>
                {data.activeJob !== null ? (
                  <StationCard
                    state={data?.activeJob.state}
                    leftS={data?.activeJob.leftS}
                    stationNumber={data?.activeJob.stationNumber}
                  />
                ) : (
                  <StationCard
                    state={"DONE"}
                    leftS={0}
                    stationNumber={data?.number}
                  />
                )}
              </Col>
            );
          } else {
            <ErrorPage
              errorHeader={t("errorDevHeader")}
              errorBody={t("errorDevBody")}
            />;
          }
        })}
      </Row>
    </Container>
  );
}
