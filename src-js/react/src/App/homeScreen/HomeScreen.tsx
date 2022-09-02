import axios from "axios";
import React, { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import ErrorPage from "../../components/error-page/ErrorPage";
import Modal from "../../components/modal/Modal";
import StationCard from "../../components/stationCard/StationCard";

const urlV2StatusAll = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/statusAll`;

export default function HomeScreen() {
  const [statusALl, setStatusAll] = useState<any>();
  const [modalActive, setModalActive] = useState(false);

  const { t } = useTranslation();
  useEffect(() => {
    try {
      axios.get(urlV2StatusAll).then(function (result: any) {
        setStatusAll(result.data);
        console.log(result.data);
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
            return (
              <Col lg={4} md={6} key={Math.random()}>
                <StationCard
                  state={data?.state}
                  leftS={data?.leftS}
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
      <Row className="justify-content-center">
        <button
          className="mt-5"
          style={{
            paddingTop: "5px",
            paddingBottom: "5px",
            paddingRight: "15px",
            paddingLeft: "15px",
            border: "none",
            borderRadius: "15px",
          }}
          onClick={() => {
            setModalActive(true);
            console.log(modalActive);
          }}
        >
          Map
        </button>
      </Row>
      <Row className="justify-content-center">
        <Modal active={modalActive} setActive={setModalActive}>
          <iframe
            src="https://www.plugshare.com/widget2.html?latitude=50.43307032970444&longitude=30.61636714055985&spanLat=0.002&spanLng=0.002&plugs=10"
            width="800"
            height="600"
            allow="geolocation"
          ></iframe>
        </Modal>
      </Row>
    </Container>
  );
}
