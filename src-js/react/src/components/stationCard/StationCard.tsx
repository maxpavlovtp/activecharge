import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import LoadingTime from "./LoadingTime";
import "./StationCard.css";
import Timer from "../timer/Timer";
import { useBackTime } from "../../hooks/useBackTime";
import { Col, Row } from "react-bootstrap";

export default function ({
  stationNumber,
  leftS,
  state,
}: {
  stationNumber: any;
  leftS: any;
  state: any;
}) {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>(0);

  useEffect(() => {
    setSecondsBackend(leftS);
  }, []);

  useBackTime(
    secondsBackend,
    hoursTime,
    setHoursTime,
    setMinuteTime,
    setSecondsTime,
    setLoading
  );

  return (
    <Link
      reloadDocument={true}
      className="linkToStation"
      to={`/start?station=${stationNumber}`}
    >
      <Row className="pl-4 pr-4 pt-4 pb-4 containerCard">
        <Row className="mainInfo">
          <Col as={"p"} className="nameStation">
            Station
          </Col>
          <Col as={"p"} className="numberStation">
            {stationNumber}
          </Col>
        </Row>
        <Row className="align-items-end">
          <Col lg={10}>
            {state === "IN_PROGRESS" ? (
              loading === true ? (
                <LoadingTime />
              ) : (
                <Timer
                  hours={hoursTime}
                  minutes={minuteTime}
                  seconds={secondsTime}
                  margin={"20px 0 0 0"}
                  fontSize={"20px"}
                />
              )
            ) : (
              <p className="readyCharge">Ready!</p>
            )}
          </Col>
          <Col lg={2}>
            <div
              className={state === "IN_PROGRESS" ? "statusCard online" : "statusCard offline"}
            ></div>
          </Col>
        </Row>
      </Row>
    </Link>
  );
}
