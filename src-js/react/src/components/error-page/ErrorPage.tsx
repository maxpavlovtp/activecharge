import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import {
  DIMA_PHONE_NUM,
  MAX_PHONE_NUM,
} from "../../App/contactsScreen/ContactsSection";
import { useAppDispatch } from "../../hooks/reduxHooks";
import { setModalOpen } from "../../store/reducers/FetchSlice";
import ModalContacts from "../modal/ModalContacts";
import styles from "./ErrorPage.module.css";

const ErrorPage = ({
  errorHeader,
  errorBody,
}: {
  errorHeader: string;
  errorBody: string;
}) => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  return (
    <div className={styles.container}>
      <p className={styles.title}>{errorHeader}</p>
      <p className={styles.body}>{errorBody}</p>
      <div
        className={styles.btnHelp}
        onClick={() => dispatch(setModalOpen(true))}
      >
        {t("helpCall")}
      </div>
      <div style={{ textAlign: "left" }}>
        <ModalContacts
          MAX_PHONE_NUM={MAX_PHONE_NUM}
          DIMA_PHONE_NUM={DIMA_PHONE_NUM}
        />
      </div>
    </div>
  );
};

export default ErrorPage;
