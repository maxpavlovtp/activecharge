import axios from "axios";
import React, { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useSearchParams } from "react-router-dom";
import ErrorPage from "../../components/error-page/ErrorPage";
import StationCard from "../../components/stationCard/StationCard";
import { useAppDispatch } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";

const urlV2StatusAll = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/statusAll`;

export default function HomeScreen() {
  const [statusALl, setStatusAll] = useState<any>();
  const [searchParams] = useSearchParams();
  let stationNumber: any = searchParams.get("station");

  const { t } = useTranslation();
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getStationInfo(stationNumber)); // use this before get data from getAllStatus endpoint
    try {
      axios.get(urlV2StatusAll).then(function (result: any) {
        setStatusAll(result.data);

        console.log(result?.data);
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);
  return (
    <Container>
      <Row className="justify-content-center">
        {statusALl?.map((data: any) => {
          if (data?.state) {
            console.log(data?.stationNumber);
            return (
              <Col lg={4} md={6} key={Math.random()}>
                <StationCard
                  state={data?.state}
                  leftS={data?.periodS}
                  stationNumber={data?.stationNumber}
                />
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
