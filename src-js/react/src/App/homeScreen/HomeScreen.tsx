import axios from "axios";
import { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import ErrorPage from "../../components/error-page/ErrorPage";
import Spinner from "../../components/spinner/Spinner";
import { StationCard } from "../../components/stationCard/StationCard";
import { getClientFingerPrint } from "../../store/reducers/ActionCreators";

const urlV2StatusAll = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/statusAll`;

export default function HomeScreen() {
  const [loading, setLoading] = useState<any>(true);
  const [statusALl, setStatusAll] = useState<any>();
  const [errorAll, setErrorAll] = useState<any>(null);

  const sec = 5000;

  const { t } = useTranslation();

  useEffect(() => {
    const clientFingerPrint = getClientFingerPrint();

    try {
      axios
        .get(`${urlV2StatusAll}?clientFingerPrint=${clientFingerPrint}`)
        .catch(function (error: any) {
          setErrorAll(error.message);
          console.log(error.message);
        })
        .then(function (result: any) {
          setStatusAll(result.data);
          statusALl !== null && setLoading(false);
          console.log(result?.data);
        });
    } catch (e: any) {
      console.log(e.message);
    }
  }, []);

  if (errorAll) {
    return (
      <ErrorPage
        errorHeader={t("errorDevHeader")}
        errorBody={t("errorDevBody")}
      />
    );
  }

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

          return;
        })}
      </Row>
    </Container>
  );
}
