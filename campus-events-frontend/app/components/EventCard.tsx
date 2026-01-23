"use client";

import { approveEvent, rejectEvent } from "../services/api";
import { useAuth } from "../context/AuthContext";

export default function EventCard({ event, reload }: any) {
  const { token, role } = useAuth();

  async function handleApprove() {
    await approveEvent(event.id, token!);
    reload();
  }

  async function handleReject() {
    await rejectEvent(event.id, token!);
    reload();
  }

  return (
    <div className="border p-4 rounded shadow">
      <h2 className="text-lg font-bold">{event.title}</h2>
      <p>{event.description}</p>
      <p className="text-sm text-gray-500">{event.location}</p>
      <p className="text-sm">Status: {event.status}</p>

      {role === "ADMIN" && event.status === "PENDING" && (
        <div className="flex gap-2 mt-2">
          <button
            onClick={handleApprove}
            className="bg-green-500 text-white px-3 py-1 rounded"
          >
            Approve
          </button>
          <button
            onClick={handleReject}
            className="bg-red-500 text-white px-3 py-1 rounded"
          >
            Reject
          </button>
        </div>
      )}
    </div>
  );
}
