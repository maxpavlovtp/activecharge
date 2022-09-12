import { useEffect, useState } from "react";
import ErrorPage from "../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../components/spinner/Spinner";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import GetPower from "../../components/getPower/GetPower";
import { useSearchParams } from "react-router-dom";
import { Container, Row } from "react-bootstrap";
import { Chart } from "../../components/charts/Chart";

const MainSection: React.FC = () => {
  const [loading, setLoading] = useState<any>(true);
  const [timer, setTimer] = useState<any>(null);
  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");

  const dispatch = useAppDispatch();

  const { t } = useTranslation();

  const { isLoadingCharging, deviceStatus, error } = useAppSelector(
    (state) => state.fetchReducer
  );

  useEffect(() => {
    if (isLoadingCharging === false) {
      dispatch(getStationInfo(stationNumbers));
      if (deviceStatus?.lastJob) {
        setTimer(
          new Date(deviceStatus?.lastJob?.leftS * 1000)
            .toISOString()
            .slice(11, 19)
        );
        console.log(timer);
      }
    }
  }, [isLoadingCharging]);

  useEffect(() => {
    console.log(deviceStatus?.lastJob?.leftS);
    if (
      deviceStatus?.lastJob?.state === "DONE" &&
      deviceStatus?.lastJob?.leftS === 0
    ) {
      setLoading(false);
    }
    if (deviceStatus?.lastJob) {
      setTimer(
        new Date(deviceStatus?.lastJob?.leftS * 1000)
          .toISOString()
          .slice(11, 19)
      );
      setLoading(false)
    }
  }, [deviceStatus?.lastJob]);

  if (error)
    return (
      <ErrorPage
        errorHeader={t("errorDevHeader")}
        errorBody={t("errorDevBody")}
      />
    );

  if (loading === true) return <Spinner />;

  return (
    <>
      <Container fluid>
        {timer !== null && (
          <>
            <GetPower station={stationNumbers} />
            {deviceStatus?.lastJob?.state === "DONE" ||
            deviceStatus?.lastJob?.state === "FAILED" ||
            deviceStatus?.lastJob?.leftS <= 3 ? (
              <></>
            ) : (
              <div
                style={{
                  textAlign: "center",
                  fontSize: "calc(1.5rem + 1.5vw)",
                  margin: "20px 0 30px 0",
                }}
              >
                {timer}
              </div>
            )}
          </>
        )}
      </Container>
    </>
  );
};

export default MainSection;
