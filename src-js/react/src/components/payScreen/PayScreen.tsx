import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { generatePaymentLink } from "../../store/reducers/ActionCreators";

export default function PayScreen() {
  const dispatch = useAppDispatch();
  const { payLink } = useAppSelector((state) => state.fetchReducer);

  const [searchParams] = useSearchParams();
  let stationNumber: any = searchParams.get("station");
  let hours: any = searchParams.get("hours");

  useEffect(() => {
    dispatch(generatePaymentLink(stationNumber, hours));
  }, []);

  return (
    <>
      {payLink === "" ? (
        <p>Wait...</p>
      ) : (
        <div style={{ height: "100vh", width: "100wh" }}>
          <iframe src='https://pay.mbnk.biz/2210202pwgCXASV1R9kD'height={"100%"} width={"100%"} />
        </div>
      )}
    </>
  );
}
