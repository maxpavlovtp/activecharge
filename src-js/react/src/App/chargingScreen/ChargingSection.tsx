import { useEffect, useState } from "react";
import Timer from "../../components/timer/Timer";
import ErrorPage from "../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../components/spinner/Spinner";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import GetPower from "../../components/getPower/GetPower";
import { useSearchParams } from "react-router-dom";
import { useBackTime } from "../../hooks/useBackTime";
import { Container, Row } from "react-bootstrap";

const MainSection: React.FC = () => {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>(0);

  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");

  const dispatch = useAppDispatch();

  const { t } = useTranslation();

  const { isLoadingCharging, deviceStatus, error } = useAppSelector(
    (state) => state.fetchReducer
  );

  useEffect(() => {
    if (isLoadingCharging === false) {
      setTimeout(() => {
        dispatch(getStationInfo(stationNumbers));
      }, 5500);
    }
  }, [isLoadingCharging]);

  useEffect(() => {
    setSecondsBackend(deviceStatus?.leftS);
    if (deviceStatus?.state === "DONE" && deviceStatus?.leftS === 0) {
      setLoading(false);
    }
  }, [deviceStatus]);

  useBackTime(
    secondsBackend,
    hoursTime,
    setHoursTime,
    setMinuteTime,
    setSecondsTime,
    setLoading
  );

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
      <Container fluid >
        {secondsTime >= 0 && (
          <>
            <GetPower station={stationNumbers} />

            <Timer
              hours={hoursTime}
              minutes={minuteTime}
              seconds={secondsTime}
              fontSize={"calc(1.5rem + 1.5vw)"}
              margin={"20px 0 30px 0"}
            />
          </>
        )}
      </Container>
    </>
  );
};

export default MainSection;
