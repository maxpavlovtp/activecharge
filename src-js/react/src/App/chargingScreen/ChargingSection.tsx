import { useEffect, useState } from "react";
import ErrorPage from "../../components/error-page/ErrorPage";
import { useTranslation } from "react-i18next";
import Spinner from "../../components/spinner/Spinner";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import GetPower from "../../components/getPower/GetPower";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Container } from "react-bootstrap";
import { useVisitorData } from "@fingerprintjs/fingerprintjs-pro-react";

const MainSection: React.FC = () => {
  const [loading, setLoading] = useState<any>(true);
  const [timer, setTimer] = useState<any>(null);
  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");

  const dispatch = useAppDispatch();

  const { data } = useVisitorData();

  const { t } = useTranslation();
  const navigate = useNavigate();

  const { isLoadingCharging, deviceStatus, errorCharging, errorStart } =
    useAppSelector((state) => state.fetchReducer);

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
      setLoading(false);
    }
  }, [deviceStatus?.lastJob]);

  useEffect(() => {
    if (errorCharging !== "") {
      setLoading(false);
    }
  }, [errorCharging]);

  if (errorCharging) {
    return (
      <ErrorPage
        errorHeader={t("errorDevHeader")}
        errorBody={t("errorDevBody")}
      />
    );
  }
  if (errorStart) {
    navigate(`/start?station=${stationNumbers}`);
  }

  if (loading === true) return <Spinner />;

  return (
    <>
      <Container fluid>
        {timer !== null && <GetPower station={stationNumbers} timer={timer} />}
      </Container>
    </>
  );
};

export default MainSection;
